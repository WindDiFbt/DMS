package controllers.admin;

import entity.Account;
import entity.Dorm;
import entity.DormRoomStatus;
import entity.Room;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import services.DormRoomStatusService;
import services.DormService;
import services.RoomService;
import services.impl.DormRoomStatusServiceImpl;
import services.impl.DormServiceImpl;
import services.impl.RoomServiceImpl;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "UpdateDormServlet", value = "/admin-dorm-update")
public class UpdateDormServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DormService dormService = new DormServiceImpl();
        String dormName = request.getParameter("dormName");
        Dorm dorm = dormService.getByDormName(dormName);

        RoomService roomService = new RoomServiceImpl();
        if (roomService.checkStudentFromDorm(dormName)) {
            request.getSession().setAttribute("failedUpdateDorm", "failed");
            response.sendRedirect(request.getContextPath() + "/admin-dorm");
            return;
        }

        DormRoomStatusService dormRoomStatusService = new DormRoomStatusServiceImpl();
        int status = Integer.parseInt(request.getParameter("status"));
        DormRoomStatus dormRoomStatus = dormRoomStatusService.getById(status);
        dorm.setStatus(dormRoomStatus);

        roomService.setRoomStatusByDormName(dormName, dormRoomStatus);

        dormService.update(dorm);
        request.getSession().setAttribute("successUpdateDorm", "success");

        response.sendRedirect(request.getContextPath() + "/admin-dorm");
    }
}