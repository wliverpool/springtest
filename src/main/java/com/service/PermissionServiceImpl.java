package com.service;

import org.springframework.cache.annotation.CacheEvict;

import com.dao.PermissionDao;
import com.dao.PermissionDaoImpl;
import com.pojo.Permission;

/**
 * 权限服务类
 * @author 吴福明
 *
 */
public class PermissionServiceImpl implements PermissionService {

    private PermissionDao permissionDao = new PermissionDaoImpl();

    public Permission createPermission(Permission permission) {
        return permissionDao.createPermission(permission);
    }

    @CacheEvict(value="permission",key="#permissionId")
    public void deletePermission(Long permissionId) {
        permissionDao.deletePermission(permissionId);
    }
}