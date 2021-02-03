package com.sperekrestova.visitCount.controllers;

import com.aspose.cells.Cells;
import com.aspose.cells.ColumnCollection;
import com.aspose.cells.RowCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import com.sperekrestova.visitCount.model.Month;
import com.sperekrestova.visitCount.model.Student;
import com.sperekrestova.visitCount.model.StudyingGroup;
import com.sperekrestova.visitCount.model.Subject;
import com.sperekrestova.visitCount.model.Timetable;
import com.sperekrestova.visitCount.model.User;
import com.sperekrestova.visitCount.repository.GroupRepository;
import com.sperekrestova.visitCount.repository.SubjectRepository;
import com.sperekrestova.visitCount.repository.TimetableRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by Svetlana
 * Date: 09.01.2021
 */
@Controller
@AllArgsConstructor
@RequestMapping("/upload")
public class UploadController {
    private final GroupRepository groupRepository;
    private final TimetableRepository timetableRepository;
    private final SubjectRepository subjectRepository;

    @GetMapping("/timetable")
    public String timetable() {
        return "uploading/upload-timetable";
    }

    @GetMapping("/marks")
    public String marks() {
        return "uploading/upload-marks";
    }

    @GetMapping("/groups")
    public String groups() {
        return "uploading/upload-groups";
    }

    @PostMapping("/timetable")
    public String parseTimetable(
            @RequestParam("file") MultipartFile reapExcelDataFile,
            @AuthenticationPrincipal User user) {

        if (user.getLectureGroups().isEmpty()) {
            return "redirect:/groups";
        }

        Workbook workbook = null;
        try {
            workbook = new Workbook(reapExcelDataFile.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Find in the file all groups which are related to the current user
        List<StudyingGroup> sg = findInTheFile(workbook, user, "group", "");
        List<String> groupsToFind = sg.stream()
                .filter(distinctByKey(StudyingGroup::getGroupName))
                .map(StudyingGroup::getGroupName)
                .collect(Collectors.toList());

        List<StudyingGroup> lectureGroups = user.getLectureGroups();

        for (StudyingGroup group : lectureGroups) {
            // For each user's group we need to find timetables if the groups are presented in the file
            if (groupsToFind.contains(group.getGroupName())) {
                List<Timetable> timetables = new ArrayList<>(
                        findInTheFile(workbook, user, "timetable", group.getGroupName())
                );
                // Set list of found timetables to the user's group
                StudyingGroup byGroupName = groupRepository.findByGroupName(group.getGroupName());
                byGroupName.getTimetables().clear();
                byGroupName.setTimetables(timetables);
                // Save to the db updated group
                //TODO: Another exception..
                groupRepository.save(byGroupName);
            }
        }

        return "redirect:/timetable";
    }

    /**
     * For each lesson of the current group we need to create a Timetable instance;
     * For each Timetable we need a Subject instance;     *
     */
    private <T> List<T> findInTheFile(Workbook workbook, User user, String toFind, String relatedTo) {
        List<StudyingGroup> lectureGroups = user.getLectureGroups();
        List<T> foundObjects = new ArrayList<>();
        String regex = "";
        switch (toFind) {
            case "group":
                regex = "[А-Я]{2,3}-\\d{2,3}.*";
                break;
            case "timetable":
                // If we're looking for a timetable for a certain group, we need to find this groups
                regex = relatedTo;
                break;
            default:
                break;
        }
        assert workbook != null;
        WorksheetCollection sheets = workbook.getWorksheets();
        for (int i = 0; i < sheets.getCount(); i++) {
            Worksheet sheet = sheets.get(i);
            Cells cells = sheet.getCells();
            // Get all columns from the sheet
            ColumnCollection columns = sheet.getCells().getColumns();
            // Iterate over the columns
            for (int currentColumn = 0; currentColumn < columns.getCount(); currentColumn++) {
                // Get all rows from the sheet
                RowCollection rows = sheet.getCells().getRows();
                // Iterate over the rows
                for (int currentRow = 0; currentRow < rows.getCount(); currentRow++) {
                    // Get the value of the row
                    String cellValue = cells.get(currentRow, currentColumn).getStringValue();
                    // Check if we took a group name
                    if (cellValue.matches(regex)) {
                        // If there are several group names in the cell, so it's a lecture class
                        if (cellValue.length() > 10 && toFind.equals("group")) {
                            // TODO: enable a possibility for tracking lectures and several groups at once
                        } else if (toFind.equals("group")) {
                            // Check if the current user has this group
                            if (checkIfProfHasThisGroup(lectureGroups, cellValue)) {
                                StudyingGroup sg = new StudyingGroup();
                                sg.setGroupName(cellValue);
                                foundObjects.add((T) sg);
                            } else {
                                // TODO: Do we need to assign a group without students to the user if it's present in the schedule?
                            }
                        } else if (toFind.equals("timetable")) {
                            // If we need to find a timetable, we need to initialize Subject and Timetable instances
                            Subject subject = initSubject(user, cells, currentColumn, currentRow);
                            //create a timetable instance
                            Timetable timetable = initTimetable(cells, currentColumn, currentRow, sheet);
                            //assign a subject to it
                            timetable.setSubject(subject);
                            timetable.setSubjectTableId(subject.getId());
                            foundObjects.add((T) timetable);
                        }
                    }
                }
            }
        }
        return foundObjects;
    }

    private Subject initSubject(User user, Cells cells, int currentColumn, int currentRow) {
        String subjectName = cells.get(currentRow - 2, currentColumn).getStringValue();
        if (subjectRepository.findByName(subjectName) != null) {
            return subjectRepository.findByName(subjectName);
        }
        Subject subject = new Subject();
        subject.setName(subjectName);
        subject.setProf(user);
        //save the subject to db and return
        return subjectRepository.save(subject);
    }

    /**
     * Rules:
     * 1. Subject's name is group name's position -2 rows;
     * 2. Lesson type for Timetable is group name's position -1 row;
     * 3. Date for timetable is sheet's name for month + pattern match [1-9]{1,2}$ for the current column;
     * 4. Time for timetable is group name's position +1 row, substr before '-';
     * 5. Classroom for timetable is group name's position +2 rows
     */
    private Timetable initTimetable(Cells cells, int currentColumn, int currentRow, Worksheet sheet) {
        int bufRow = currentRow;
        String lessonType = cells.get(bufRow - 1, currentColumn).getStringValue();
        bufRow = currentRow;
        String bufTime = cells.get(bufRow + 1, currentColumn).getStringValue();
        int hour = Integer.parseInt(bufTime.substring(0, 2));
        int minute = Integer.parseInt(bufTime.substring(3, 5));
        LocalTime time = LocalTime.of(hour, minute);
        int year = Year.now().getValue();
        int month = Month.valueOf(sheet.getName().toUpperCase()).getValue();
        String day;
        bufRow = currentRow;
        do {
            day = cells.get(bufRow--, currentColumn).getStringValue();
        } while (!day.matches("[1-9]{1,2}$"));

        LocalDate date = LocalDate.of(year, month, Integer.parseInt(day));
        bufRow = currentRow;
        String classRoom = cells.get(bufRow + 2, currentColumn).getStringValue();
        Timetable timetable = new Timetable();
        timetable.setLessonType(lessonType);
        timetable.setTime(time);
        timetable.setDate(date);
        timetable.setClassroom(classRoom);
        return timetable;
    }

    private boolean checkIfProfHasThisGroup(List<StudyingGroup> lectureGroups, String cellValue) {
        for (StudyingGroup group : lectureGroups) {
            if (group.getGroupName().equals(cellValue)) {
                return true;
            }
        }
        return false;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    @PostMapping("/groups")
    public String parseGroups(
            @RequestParam("file") MultipartFile reapExcelDataFile,
            @AuthenticationPrincipal User user) throws IOException {
        List<Student> tempStudentList = new ArrayList<>();

        HSSFWorkbook workbook = new HSSFWorkbook(reapExcelDataFile.getInputStream());

        HSSFSheet worksheet = workbook.getSheetAt(0);
        //Parse group name
        String groupName = worksheet.getRow(0).getCell(1).getStringCellValue();
        //create group instance
        StudyingGroup studyingGroup = new StudyingGroup(groupName);

        //When the group already exists in the system
        // And if this is a different user, we need to assign him to the existed group
        if (groupRepository.findByGroupName(groupName) != null) {
            if (!user.getLectureGroups().contains(studyingGroup)) {
                StudyingGroup existedGroup = groupRepository.findByGroupName(groupName);
                List<User> profs = existedGroup.getProfs();
                profs.add(user);
                existedGroup.setProfs(profs);
                groupRepository.save(existedGroup);
            }
            return "redirect:/groups";
        }

        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            //New student instance
            Student student = new Student();
            //Assign group to the student
            student.setGroup(studyingGroup);
            //Parse row
            HSSFRow row = worksheet.getRow(i);
            if (row.getCell(0) == null || row.getCell(0).getCellType() == CellType.BLANK) {
                break;
            }
            //Set student's id number
            student.setId((long) row.getCell(0).getNumericCellValue());
            //Parse the full name
            String fullName = row.getCell(1).getStringCellValue();
            List<String> list = new LinkedList<>(Arrays.asList(fullName.split(" ")));
            //Set student's info
            student.setLastName(list.get(0));
            student.setFirstName(list.get(1));
            student.setSecondName(list.get(2));
            //Add to the student's list
            tempStudentList.add(student);
        }
        studyingGroup.setStudents(tempStudentList);

        studyingGroup.setProfs(new ArrayList<>(List.of(user)));
        //Save group to the database
        groupRepository.save(studyingGroup);

        return "redirect:/groups";
    }

}
