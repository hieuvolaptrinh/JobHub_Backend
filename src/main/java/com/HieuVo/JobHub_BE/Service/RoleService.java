package com.HieuVo.JobHub_BE.Service;


import com.HieuVo.JobHub_BE.DTO.Response.ResultPaginationDTO;
import com.HieuVo.JobHub_BE.Model.Permission;
import com.HieuVo.JobHub_BE.Model.Role;
import com.HieuVo.JobHub_BE.repository.PermissionRepository;
import com.HieuVo.JobHub_BE.repository.RoleRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;


@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    public boolean isExistsByName(String name) {
        return this.roleRepository.existsByName(name);
    }

    public Role createRole(Role role) {
        if (role.getPermissions() != null) {
            List<Long> reqPermission = role.getPermissions().stream().map(x -> x.getId()).collect(Collectors.toList());
            List<Permission> currentPermission = this.permissionRepository.findByIdIn(reqPermission);
            role.setPermissions(currentPermission);
        }
        return this.roleRepository.save(role);
    }

    public Role getRoleById(long id) {
        Optional<Role> currentRole = this.roleRepository.findById(id);
        if (currentRole.isPresent()) {
            return currentRole.get();
        }
        return null;
    }

    public Role updateRole(Role role) {
        Role currentRole = this.getRoleById(role.getId());
        if (role.getPermissions() != null) {
            List<Long> reqPermission = role.getPermissions().stream().map(x -> x.getId()).collect(Collectors.toList());
            List<Permission> currentPermission = this.permissionRepository.findByIdIn(reqPermission);
            role.setPermissions(currentPermission);
        }
        currentRole.setName(role.getName());
        currentRole.setDescription(role.getDescription());
        currentRole.setActive(role.isActive());
        currentRole.setPermissions(role.getPermissions());
        currentRole = this.roleRepository.save(currentRole);
        return currentRole;
    }

    public void deleteRole(long id) {
        this.roleRepository.deleteById(id);
    }

    public ResultPaginationDTO getAllRole(Specification<Role> specification, Pageable pageable) {
        Page<Role> pageRole = this.roleRepository.findAll(specification, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta meta = new ResultPaginationDTO.Meta();
        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(pageRole.getTotalPages());
        meta.setTotal(pageRole.getTotalElements());
        rs.setMeta(meta);
        rs.setResult(pageRole.getContent());
        return rs;
    }
}
