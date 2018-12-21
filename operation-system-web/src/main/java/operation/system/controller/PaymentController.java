package operation.system.controller;

import com.framework.manager.IManager;
import com.framework.web.controller.BaseController;
import operation.system.manager.IPaymentManager;
import operation.system.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/payment/*")
public class PaymentController extends BaseController<Payment, String> {

    @Autowired
    private IPaymentManager manager;

    @Override
    protected IManager<Payment, String> getManager() {
        return manager;
    }

    @Override
    protected String getTemplateFolder() {
        return "/payment/";
    }

    @RequestMapping("/test_op")
    @ResponseBody
    public String test() {
        return "test_op";
    }
}
