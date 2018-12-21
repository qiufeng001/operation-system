package operation.system.manager.impl;

import com.framework.manager.impl.BaseManager;
import com.framework.service.IService;
import operation.system.manager.IPaymentManager;
import operation.system.model.Payment;
import operation.system.service.IPaymentServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentManagerImpl extends BaseManager<Payment, String> implements IPaymentManager {

    @Autowired
    private IPaymentServcie servcie;

    @Override
    protected IService getService() {
        return servcie;
    }
}
