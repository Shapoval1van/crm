package com.becomejavasenior.controller;

import com.becomejavasenior.dao.DatabaseException;
import com.becomejavasenior.model.*;
import com.becomejavasenior.service.CompanyService;
import com.becomejavasenior.service.UserService;
import com.becomejavasenior.service.impl.CompanyServiceImpl;
import com.becomejavasenior.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@WebServlet("/addCompany")
public class AddCompanyController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(AddCompanyController.class);

    private static final String ADD_COMPANY_PAGE = "/pages/addCompany.jsp";
    private static final String COMPANY_NAME = "companyName";
    private static final String COMPANY_TAGS = "companyTags";
    private static final String COMPANY_RESPONSIBLE = "companyResponsible";

    private static final String COMPANY_PHONE_TYPE = "companyPhoneType";
    private static final String COMPANY_PHONE = "companyPhone";
    private static final String COMPANY_EMAIL = "companyEmail";
    private static final String COMPANY_WEB = "companyWeb";
    private static final String COMPANY_ADDRESS = "companyAddress";
    private static final String COMPANY_NOTE = "companyNote";

    private static final String CONTACT_NAME = "contactName";
    private static final String CONTACT_POSITION = "contactPosition";
    private static final String CONTACT_PHONE_TYPE = "contactPhoneType";
    private static final String CONTACT_PHONE = "contactPhone";
    private static final String CONTACT_EMAIL = "contactEmail";
    private static final String CONTACT_SKYPE = "contactSkype";

    private static final String DEAL_NAME = "dealName";
    private static final String DEAL_STAGES = "dealStages";
    private static final String DEAL_STAGE = "dealStage";
    private static final String DEAL_BUDGET = "dealBudget";

    private static final String TASK_NAME = "taskName";
    private static final String TASK_RESPONSIBLE = "taskResponsible";
    private static final String TASK_DESCRIPTION = "taskDescription";
    private static final String TASK_PERIOD = "taskPeriod";
    private static final String TASK_PERIODS = "taskPeriods";
    private static final String TASK_DATE = "taskDate";

    private static final String PHONE_TYPES = "phoneTypes";
    private static final String USERS_LIST = "usersList";


    private UserService userService = new UserServiceImpl();
    private CompanyService companyService = new CompanyServiceImpl();
    private Company company = new Company();



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = null;
        try {
            users = userService.findAll();
        } catch (DatabaseException e) {
            LOGGER.error("Couldn't get users data!", e.getMessage());
        }
        req.setAttribute(USERS_LIST, users);
        req.setAttribute(PHONE_TYPES, PhoneType.values());
        req.setAttribute(DEAL_STAGES, DealStage.values());
        req.setAttribute(TASK_PERIODS, Period.values());
        RequestDispatcher rd = getServletContext().getRequestDispatcher(ADD_COMPANY_PAGE);
        rd.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> users = null;
        try {
            users = userService.findAll();
        } catch (DatabaseException e) {
            LOGGER.error("Couldn't get users data!", e.getMessage());
        }

        company.setName(req.getParameter(COMPANY_NAME));
        if (req.getParameter(COMPANY_TAGS) != null) {
            company.setTags(InputDataManager.stringToTags(req.getParameter(COMPANY_TAGS)));
        }

        company.setResponsibleUser(
                users.stream()
                        .filter((u) -> u.getId() == Integer.parseInt(req.getParameter(COMPANY_RESPONSIBLE)))
                        .findFirst()
                        .get()
        );


        company.setCreatedByUser(company.getResponsibleUser());
        company.setEmail(req.getParameter(COMPANY_EMAIL));
        company.setWeb(req.getParameter(COMPANY_WEB));
        company.setAddress(req.getParameter(COMPANY_ADDRESS));
        company.setPhoneType(PhoneType.valueOf(req.getParameter(COMPANY_PHONE_TYPE)));
        company.setPhone(req.getParameter(COMPANY_PHONE));

        String noteString = req.getParameter(COMPANY_NOTE);

        if (noteString != null && !"".equals(noteString)) {
            final Note note = new Note();
            note.setText(noteString);
            company.setNotes(Collections.singletonList(note));
        }

        String contactName = req.getParameter(CONTACT_NAME);
        if( !"".equals(contactName) && contactName != null) {
            final Contact contact = new Contact();
            contact.setName(contactName);
            contact.setPosition(req.getParameter(CONTACT_POSITION));
            contact.setPhoneType(PhoneType.valueOf(req.getParameter(CONTACT_PHONE_TYPE)));
            contact.setPhone(req.getParameter(CONTACT_PHONE));
            contact.setEmail(req.getParameter(CONTACT_EMAIL));
            contact.setSkype(req.getParameter(CONTACT_SKYPE));

            company.setContacts(Collections.singletonList(contact));
        }

        String dealName = req.getParameter(DEAL_NAME);
        if( !"".equals(dealName) && dealName != null) {
            Deal deal = new Deal();
            deal.setName(dealName);
            deal.setDealStage(DealStage.valueOf(req.getParameter(DEAL_STAGE)));
            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setParseBigDecimal(true);

            if( !"".equals(req.getParameter(DEAL_BUDGET))) {
                BigDecimal budget = null;
                try {
                    budget = (BigDecimal) decimalFormat.parse(req.getParameter(DEAL_BUDGET));
                } catch (ParseException e) {
                    LOGGER.error("Parsing error!", e.getMessage());
                }
                deal.setBudget(budget);
            }
        }

        String taskName = req.getParameter(TASK_NAME);
        if( !"".equals(taskName) && taskName != null) {
            Task task = new Task();
            task.setName(taskName);
           // task.setPeriod(Period.valueOf(req.getParameter(TASK_PERIOD)));
/*
            try {
                task.setFinishDate(new SimpleDateFormat().parse(req.getParameter(TASK_DATE)) );
            } catch (ParseException e) {
                e.printStackTrace();
            }
*/
            task.setResponsibleUser(
                    users.stream()
                            .filter((u) -> u.getId() == Integer.parseInt(req.getParameter(TASK_RESPONSIBLE)))
                            .findFirst()
                            .get()
            );
            task.setDescription(req.getParameter(TASK_DESCRIPTION));
        }

        try {
            companyService.create(company);
        } catch (DatabaseException e) {
            LOGGER.error("Couldn't save company!", e.getMessage());
        }
        resp.sendRedirect("/dashboard");
    }

}