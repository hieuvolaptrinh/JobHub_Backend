package com.HieuVo.JobHub_BE.Service;

import com.HieuVo.JobHub_BE.DTO.Response.ResultPaginationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.HieuVo.JobHub_BE.Model.Skill;
import com.HieuVo.JobHub_BE.repository.SkillRepository;

import java.util.Optional;

@Service
public class SkillService {

    private final SkillRepository skillRepository;

    public SkillService(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    public boolean isNameExist(String name) {
        return this.skillRepository.existsByName(name);
    }

    public Skill fetchSkillById(long id) {
        return this.skillRepository.findById(id).orElse(null);
    }

    public Skill handleCreateSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    public Skill handleUpdateSkill(Skill skill) {
        return this.skillRepository.save(skill);
    }

    public ResultPaginationDTO fetchAllSkill(Specification<Skill> spec, Pageable pageable) {
        Page<Skill> pageUser = this.skillRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();

        meta.setPage(pageable.getPageNumber());
        meta.setPageSize(pageable.getPageSize());
        meta.setPage(pageUser.getNumber());
        meta.setTotal(pageUser.getTotalPages());

        rs.setMeta(meta);
        rs.setResult(pageUser.getContent());
        return rs;
    }

    public boolean isSkillExist(long id) {
        return this.skillRepository.existsById(id);
    }

    public void deleteSkill(long id) {
        Skill skill = this.skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found with id: " + id));
        // Remove the skill from all jobs
        skill.getJobs().forEach(job -> job.getSkills().remove(skill));
        this.skillRepository.deleteById(id);
    }
}
