package west.orm.core;

import com.bucuoa.west.orm.core.base.IdObject;
import com.bucuoa.west.orm.core.uitls.AnnoationUtil;

public class TestAnnoationUtil {

	public static void main(String[] args) {
		Activity act = new Activity();
		act.setNumLimit(100);
		act.setPlaceId(123221);
		act.setTitle("生成代码");

		
		final String idname = AnnoationUtil.getId(UlewoUser.class);
		System.out.println(idname);

		final IdObject object = AnnoationUtil.getIdObject(Activity.class);

		System.out.println(object.getName()+"=?"+object.getPkStrategy());
	}

}
