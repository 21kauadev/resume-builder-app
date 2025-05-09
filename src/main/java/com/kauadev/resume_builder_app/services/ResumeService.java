package com.kauadev.resume_builder_app.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public Resume uploadResume(MultipartFile file, String position) throws IOException {

        // salvando localmente

        // gerando um nome aleatorio com base em um UUID e o nome orignial do arquivo
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // objeto path, representa o caminho no disco onde será salvo
        Path path = Paths.get("uploads/" + filename);

        // escreve o conteúdo binário do arquivo enviado no caminho especificado pelo
        // path
        Files.write(path, file.getBytes());

        Resume resume = new Resume(path.toString(), position, getLoggedUser());

        return resumeRepository.save(resume);
    }

    public void deleteResume(Long id) {
        Resume resume = resumeRepository.findById(id).orElseThrow(ResumeNotFoundException::new);

        resumeRepository.delete(resume);
    }

}
