import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.master.dbmaamger.dao.BaseDao;
import project.master.dbmaamger.service.AbstractService;

@Component
public class CreateFileServiceImpl extends AbstractService<CreateFile, Integer> implements UserService {

	@Autowired
	private CreateFileDao createFileDao;

	@Override
	public BaseDao<CreateFile, Integer> getService() {
		return CreateFileDao;
	}

}
