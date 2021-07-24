package test;

import annotation.Service;
import api.ByeService;

/**
 * @author tangssst@qq.com
 */
@Service
public class ByeServiceImpl implements ByeService {

    @Override
    public String bye(String name) {
        return "bye, " + name;
    }
}
