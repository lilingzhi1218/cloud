package com.example.llz.cloud2.form;

import com.example.llz.cloud2.form.dao.IFormClassDao;
import com.example.llz.cloud2.form.dao.IFormDomainInfoDao;
import com.example.llz.cloud2.form.dao.IFormInfoDao;
import com.example.llz.cloud2.form.entity.Formdomaininfo;
import com.example.llz.cloud2.util.CheckUtil;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("form")
public class FormController {
    
    @Autowired
    private IFormDomainInfoDao formDomainInfoDao;
    @Autowired
    private IFormInfoDao formInfoDao;
    @Autowired
    private IFormClassDao formClassDao;
    
    @RequestMapping("findByCondition")
    public void findByCondition(String condition){
        long checkTime = CheckUtil.checkTime(0, "查询");
        Iterable<Formdomaininfo> all = formDomainInfoDao.findByCondition("%"+condition+"%");
        CheckUtil.checkTime(checkTime, "查询所有");
        CheckUtil.checkTime(checkTime, "查询所有");
    }

    @RequestMapping("addFormDomain")
    @Transactional
    public void addFormDomain(){
        long checkTime = CheckUtil.checkTime(0, "查询");
        Formdomaininfo formdomaininfo = new Formdomaininfo();
        formdomaininfo.setRid(UUID.randomUUID().toString());
        formDomainInfoDao.save(formdomaininfo);
        CheckUtil.checkTime(checkTime, "查询所有");
    }
    
}
