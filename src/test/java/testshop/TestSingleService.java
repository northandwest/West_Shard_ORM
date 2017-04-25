package testshop;

public class TestSingleService {

	public static void main(String[] args) {
		ShopService service = new ShopService();
		try {
			service.deleteEntityById(12l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			service.findEntityById(12l);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		service.findEntityListBy(entity);
//		service.findEntityList(where, page);
//		
//		service.findEntityList(orderBy, page);
//		
//		service.getEntityCount(es);
//		
//		service.saveEntity(entity);
//		
//		service.updateEntity(entity);
//		
//		service.updateEntityIn(t, ids);
//		
//		service.findEntityById(id);
//		service.findEntityBy(entity);
//		service.findEntityListBy(entity);
		
		
//		service.deleteEntityBy(entity)
//		service.deleteEntityBy(entity);
	}

}
