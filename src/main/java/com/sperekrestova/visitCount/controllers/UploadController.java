package com.sperekrestova.visitCount.controllers;

import com.aspose.cells.Cells;
import com.aspose.cells.ColumnCollection;
import com.aspose.cells.RowCollection;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;
import com.sperekrestova.visitCount.model.Student;
import com.sperekrestova.visitCount.model.StudyingGroup;
import com.sperekrestova.visitCount.model.User;
import com.sperekrestova.visitCount.repository.GroupRepository;
import com.sperekrestova.visitCount.repository.UserRepository;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Svetlana
 * Date: 09.01.2021
 */
@Controller
@AllArgsConstructor
@RequestMapping("/upload")
public class UploadController {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

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
            @AuthenticationPrincipal User user) throws IOException {

        List<StudyingGroup> lectureGroups = user.getLectureGroups();
        Workbook workbook = null;
        try {
            workbook = new Workbook(reapExcelDataFile.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert workbook != null;
        WorksheetCollection sheets = workbook.getWorksheets();
        for (int i = 0; i < sheets.getCount(); i++) {
            Worksheet sheet = sheets.get(i);
            sheet.getCells().deleteBlankRows();
            sheet.getCells().deleteBlankColumns();
            Cells cells = sheet.getCells();
            // Get all columns from the sheet
            ColumnCollection columns = sheet.getCells().getColumns();
            // Iterate over the columns
            for (int j = 0; j < columns.getCount(); j++) {
                // Get all rows from the sheet
                RowCollection rows = sheet.getCells().getRows();
                // Iterate over the rows
                for (int k = 0; k < rows.getCount(); k++) {
                    // Get the value of the row
                    String cellValue = cells.get(k, j).getStringValue();
                    // Check if we took a group name
                    if (cellValue.matches("[А-Я]{2,3}-\\d{2,3}.*")) {
                        // If there are several group names in the cell, so it's a lecture class
                        if (cellValue.length() > 10) {
                            // TODO: enable a possibility for tracking lectures and several groups at once
                        } else {
                            // Check if the current user has this group
                            if (checkIfProfHasThisGroup(lectureGroups, cellValue)) {
                                /**
                                 * For each lesson of the current group we need to create a Timetable instance;
                                 * For each Timetable we need a Subject instance;
                                 *
                                 */
                            } else {
                                // TODO: Do we need to assign a group without students to the user if it's present in the schedule
                            }
                        }
                    }
                }
            }
        }

        /**
         * Парсить группу, для каждой группы создавать timetable,
         * в него кидать инстанс предмета и все остальное.
         */

        return "redirect:/timetable";
    }

    private boolean checkIfProfHasThisGroup(List<StudyingGroup> lectureGroups, String cellValue) {
        for (StudyingGroup group : lectureGroups) {
            if (group.getGroupName().equals(cellValue)) {
                return true;
            }
        }
        return false;
    }

    @PostMapping("/groups")
    public String parseGroups(
            @RequestParam("file") MultipartFile reapExcelDataFile,
            @AuthenticationPrincipal User user) throws IOException {
        List<Student> tempStudentList = new ArrayList<Student>();

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
