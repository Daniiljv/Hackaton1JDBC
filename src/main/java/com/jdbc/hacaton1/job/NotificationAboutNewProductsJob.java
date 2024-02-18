package com.jdbc.hacaton1.job;

import com.jdbc.hacaton1.dao.impl.ProductsDaoImpl;
import com.jdbc.hacaton1.dao.impl.UsersDaoImpl;
import com.jdbc.hacaton1.models.ProductsFeed;
import com.jdbc.hacaton1.models.UsersModel;
import com.jdbc.hacaton1.services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class NotificationAboutNewProductsJob {

    private final ProductsDaoImpl productsDao;
    private final UsersDaoImpl usersDao;
    private final MailService mailService;

    //@Scheduled(fixedDelay = 15000)
    @Scheduled(cron = "0 0 12 * * ?")
    void NotificationAboutProductsToEvaluate(){
        List<UsersModel> activeUsers = usersDao.getAllUsers();

        if(!activeUsers.isEmpty()){
            for(UsersModel user : activeUsers){
                List<ProductsFeed> productsWithoutEvaluation = productsDao.getAllProductsWithoutEvaluation(user.getId());

                if(!productsWithoutEvaluation.isEmpty() && user.getMailAddress() != null){
                    mailService.notifyUserToEvaluateProducts(user.getLogin(), user.getMailAddress());
                }
            }
        }
    }
}
