package operation.system.service.impl;

import com.framework.core.base.domain.IRepository;
import com.framework.service.impl.BaseService;
import operation.system.domain.IPaymentReponsitory;
import operation.system.model.Payment;
import operation.system.service.IPaymentServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@SuppressWarnings("ALL")
@Service
public class PaymentServiceImpl extends BaseService<Payment, String> implements IPaymentServcie {

    @Autowired
    private IPaymentReponsitory reponsitory;

    protected IRepository<Payment, String> getRepository() {
        return reponsitory;
    }
}
