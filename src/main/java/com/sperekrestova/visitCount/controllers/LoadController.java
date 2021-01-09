package com.sperekrestova.visitCount.controllers;

import com.sperekrestova.visitCount.model.Student;
import com.sperekrestova.visitCount.model.StudyingGroup;
import com.sperekrestova.visitCount.repository.GroupRepository;
import com.sperekrestova.visitCount.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@RequestMapping("/load")
public class LoadController {
    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;

    @GetMapping("/timetable")
    public String timetable(Model model) {
        return "loading/load-timetable";
    }

    @GetMapping("/marks")
    public String marks(Model model) {
        return "loading/load-marks";
    }

    @GetMapping("/groups")
    public String groups(Model model) {
        return "loading/load-groups";
    }

    @PostMapping("/groups")
    public String parseGroups(@RequestParam("file") MultipartFile reapExcelDataFile) throws IOException {
        List<Student> tempStudentList = new ArrayList<Student>();

        HSSFWorkbook workbook = new HSSFWorkbook(reapExcelDataFile.getInputStream());

        HSSFSheet worksheet = workbook.getSheetAt(0);
        //Parse group name
        String groupName = worksheet.getRow(0).getCell(1).getStringCellValue();
        //create group instance
        StudyingGroup studyingGroup = new StudyingGroup(groupName);

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
        //Save group to the database
        groupRepository.save(studyingGroup);

        return "redirect:/groups";
    }

}
