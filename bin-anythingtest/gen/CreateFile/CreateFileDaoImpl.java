import org.springframework.stereotype.Component;
import project.master.dbmaamger.DBAdapter;
import project.master.dbmaamger.dao.AbstractDao;

@Component
public class CreateFileDaoImpl extends AbstractDao<CreateFile, Integer> implements CreateFileDao {

	@Autowired
	private DBAdapter dBAdapter;

	@Override
	public Class<CreateFile> getEntityClass() {
		return CreateFile.class;
	}

	@Override
	public DBAdapter getDBAdapter() {
		return dBAdapter;
	}

}
