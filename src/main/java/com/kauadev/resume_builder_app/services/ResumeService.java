package com.kauadev.resume_builder_app.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kauadev.resume_builder_app.domain.resume.Resume;
import com.kauadev.resume_builder_app.domain.resume.exceptions.AdminCanNotHaveResumesException;
import com.kauadev.resume_builder_app.domain.resume.exceptions.ResumeNotFoundException;
import com.kauadev.resume_builder_app.domain.user.User;
import com.kauadev.resume_builder_app.repositories.ResumeRepository;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    private User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User loggedUser = (User) auth.getPrincipal();

        return loggedUser;
    }

    public List<Resume> getAllResumes() {
        List<Resume> resumes = resumeRepository.findAll();

        return resumes;
    }

    public Resume getResume(Long id) {
        Resume resume = resumeRepository.findById(id).orElseThrow(ResumeNotFoundException::new);

        return resume;
    }

    // só usuario candidato pode ver seu proprio curriculo, pois
    // admins não postam curriculos. eles verão os curriculos postados pelos
    // candidatos
    public Resume getUserResume() {
        User loggedUser = this.getLoggedUser();

        if (loggedUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AdminCanNotHaveResumesException();
        }

        // checando todos os resumes
        List<Resume> resumes = resumeRepository.findAll();

        // filtra, compara se o id do usuario que enfviou o resume é o mesmo do logado
        // se sim, por ser apenas um, retorna o índice 0, o primeiro índice.
        // retornando apenas o objeto
        Resume userResume = resumes
                .stream()
                .filter((resume) -> resume.getUser().getId() == loggedUser.getId()).toList()
                .get(0);

        return userResume;
    }
}
