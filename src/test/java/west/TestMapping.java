package west;

import com.bucuoa.west.orm.core.mapping.DefaultMappingFactory;
import com.bucuoa.west.orm.core.mapping.Mapping;
import com.bucuoa.west.orm.core.mapping.MappingFactory;

public class TestMapping {

	public static void main(String[] args) {
		MappingFactory factory = new DefaultMappingFactory();
		Mapping builder = factory.createMysql();
//		builder.buildDelete(t);
		
	}

}
