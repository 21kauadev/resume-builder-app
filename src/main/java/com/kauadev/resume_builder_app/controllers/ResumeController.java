package com.kauadev.resume_builder_app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kauadev.resume_builder_app.domain.resume.Resume;
import com.kauadev.resume_builder_app.domain.resume.ResumeDTO;
import com.kauadev.resume_builder_app.infra.ApiResponse;
import com.kauadev.resume_builder_app.services.ResumeService;

@RestController
@RequestMapping("/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @GetMapping("/")
    private ResponseEntity<ApiResponse<List<Resume>>> getAllResumes() {
        List<Resume> resumes = resumeService.getAllResumes();

        ApiResponse<List<Resume>> response = new ApiResponse<List<Resume>>(HttpStatus.OK.value(), true,
                "Currículos retornados: ", resumes);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ApiResponse<Resume>> getResume(@PathVariable("id") Long id) {
        Resume resume = resumeService.getResume(id);

        ApiResponse<Resume> response = new ApiResponse<Resume>(HttpStatus.OK.value(), true, "Currículo retornado: ",
                resume);

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/userResume")
    private ResponseEntity<ApiResponse<Resume>> getUserResume() {
        Resume userResume = resumeService.getUserResume();

        ApiResponse<Resume> response = new ApiResponse<Resume>(HttpStatus.OK.value(), true, "Currículo retornado: ",
                userResume);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping()
    private ResponseEntity<ApiResponse<Resume>> createResume(ResumeDTO data) {
        Resume resume = resumeService.createResume(data);

        ApiResponse<Resume> response = new ApiResponse<Resume>(HttpStatus.OK.value(), true,
                "Currículo enviado com sucesso.",
                resume);

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping()
    private ResponseEntity<ApiResponse<Void>> deleteResume(Long id) {
        resumeService.deleteResume(id);

        ApiResponse<Void> response = new ApiResponse<Void>(HttpStatus.OK.value(), true, "Currículo apagado.", null);

        return ResponseEntity.ok().body(response);
    }
}
