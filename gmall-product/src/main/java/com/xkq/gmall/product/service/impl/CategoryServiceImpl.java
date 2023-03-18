package com.xkq.gmall.product.service.impl;

import com.xkq.gmall.product.service.CategoryBrandRelationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xkq.common.utils.PageUtils;
import com.xkq.common.utils.Query;

import com.xkq.gmall.product.dao.CategoryDao;
import com.xkq.gmall.product.entity.CategoryEntity;
import com.xkq.gmall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Resource
    CategoryBrandRelationService relationService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询树形三级分类
     * @return
     */
    @Override
    public List<CategoryEntity> listWithTree() {
        // 查出所有分类
        List<CategoryEntity> categoryEntities = baseMapper.selectList(null);

        //组装成父子的树形结构
        //找到所有的一级分类
        List<CategoryEntity> collect = categoryEntities.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .map((item) -> {
                    item.setChildren(getChildren(item, categoryEntities));
                    return item;
                }).sorted((item1, item2) -> {
                    return (item1.getSort() == null ? 0 : item1.getSort()) - (item2.getSort() == null ? 0 : item2.getSort());
                }).collect(Collectors.toList());

        return collect;
    }

    @Override
    public Long[] findCategoryPath(Long categoryId) {
        List<Long> parentPath = this.findParentPath(new ArrayList<>(), categoryId);
        return parentPath.toArray(new Long[0]);
    }

    @Transactional
    @Override
    public void updateDetail(CategoryEntity category) {
        baseMapper.updateById(category);

        if (StringUtils.hasLength(category.getName())) {
            //同步更新其他关联表中的数据
            relationService.updateCategory(category.getCatId(), category.getName());
        }
    }

    /**
     * 递归从下到上查询完整路径
     * @param pathList
     * @param categoryId
     * @return
     */
    private List<Long> findParentPath(List<Long> pathList, Long categoryId) {
        CategoryEntity entity = this.getById(categoryId);
        if (entity.getParentCid() != 0) {
            this.findParentPath(pathList, entity.getParentCid());
        }
        pathList.add(entity.getCatId());
        return pathList;
    }

    /**
     * 递归查询所有菜单的子菜单
     * @param root
     * @param list
     * @return
     */
    private List<CategoryEntity> getChildren(CategoryEntity root, List<CategoryEntity> list) {
        List<CategoryEntity> collect = list.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == root.getCatId())
                .map(categoryEntity -> {
                    //1.找到子菜单
                    categoryEntity.setChildren(getChildren(categoryEntity, list));
                    return categoryEntity;
                }).sorted((item1, item2) -> {
                    //2.菜单排序
                    return (item1.getSort() == null ? 0 : item1.getSort()) - (item2.getSort() == null ? 0 : item2.getSort());
                }).collect(Collectors.toList());
        return collect;
    }

}