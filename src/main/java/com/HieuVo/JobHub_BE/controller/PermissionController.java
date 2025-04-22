package com.HieuVo.JobHub_BE.controller;



import com.HieuVo.JobHub_BE.DTO.Response.ResultPaginationDTO;
import com.HieuVo.JobHub_BE.Model.Permission;
import com.HieuVo.JobHub_BE.Service.PermissionService;
import com.HieuVo.JobHub_BE.Util.Anotation.ApiMessage;
import com.HieuVo.JobHub_BE.Util.Error.IdInvalidException;
import com.turkraft.springfilter.boot.Filter;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PermissionController {
    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PostMapping("/permissions")
    @ApiMessage("Create permision success")
    public ResponseEntity<Permission> createPermission(@Valid @RequestBody Permission permission)
            throws IdInvalidException {
        if (this.permissionService.isPermissionExist(permission)) {
            throw new IdInvalidException("Permission is already exist");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.permissionService.createPermission(permission));
    }

    @PutMapping("/permissions")
    @ApiMessage("Update permision success")
    public ResponseEntity<Permission> updatePermission(@Valid @RequestBody Permission permission)
            throws IdInvalidException {
        if (this.permissionService.getPermissionById(permission.getId()) == null) {
            throw new IdInvalidException("Permission is not exist");
        }
        // if (this.permissionService.isPermissionExist(permission)) {
        // throw new IdInvalidException("Permission is already exist");
        // }
        return ResponseEntity.ok().body(this.permissionService.updatePermission(permission));
    }

    @DeleteMapping("permissions/{id}")
    @ApiMessage("Delete permision success")
    public ResponseEntity<Void> deletePermission(@PathVariable("id") long id) throws IdInvalidException {
        if (this.permissionService.getPermissionById(id) == null) {
            throw new IdInvalidException("Permission is not exist");
        }
        this.permissionService.deletePermission(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/permissions")
    @ApiMessage("Get all permision success")
    public ResponseEntity<ResultPaginationDTO> getAllPermission(@Filter Specification<Permission> specification,
                                                                Pageable pageable) {

        return ResponseEntity.ok().body(this.permissionService.getAllPermission(specification, pageable));
    }

}
