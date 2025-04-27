package com.HieuVo.JobHub_BE.controller;

import java.util.List;

import com.HieuVo.JobHub_BE.DTO.Response.ResultPaginationDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.HieuVo.JobHub_BE.Model.Skill;
import com.HieuVo.JobHub_BE.Service.SkillService;
import com.HieuVo.JobHub_BE.Util.Anotation.ApiMessage;
import com.HieuVo.JobHub_BE.Util.Error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/skills")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @PostMapping("/create")
    @ApiMessage("Create new skill successed")
    public ResponseEntity<Skill> createSkill(@RequestBody @Valid Skill skill) throws IdInvalidException {
        if (skill.getName() == null && skillService.isNameExist(skill.getName())) {
            throw new IdInvalidException("Skill name" + skill.getName() + "đã tồn tại");
        }

        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.handleCreateSkill(skill));
    }

    @PutMapping("/update")
    @ApiMessage("Update skill successed")
    public ResponseEntity<Skill> updateSkill(@RequestBody @Valid Skill skill)
            throws IdInvalidException {

        Skill currentSkill = this.skillService.fetchSkillById(skill.getId());
        if (currentSkill == null) {
            throw new IdInvalidException("Skill " + skill.getId() + " không tồn tại");
        }
        if (skill.getName() == null && skillService.isNameExist(skill.getName())) {
            throw new IdInvalidException("Skill name" + skill.getName() + "đã tồn tại");
        }
        currentSkill.setName(skill.getName());
        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.handleUpdateSkill(skill));
    }


    @GetMapping("")
    @ApiMessage("Get all skills successed")
    public ResponseEntity<ResultPaginationDTO> getAllSkills(@Filter Specification<Skill> spec, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(this.skillService.fetchAllSkill(spec, pageable));
    }


    @DeleteMapping("/delete/{id}")
    @ApiMessage("Delete skill successed")
    public ResponseEntity<Void> deleteSkill(@PathVariable long id) throws IdInvalidException {
        if (!skillService.isSkillExist(id)) {
            throw new IdInvalidException("Skill " + id + " không tồn tại");
        }
        this.skillService.deleteSkill(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
