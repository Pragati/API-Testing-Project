import org.testng.annotations.Test;

public class testFile {

    EventCreation ec = new EventCreation();

    @Test
    void test_01(){
        ec.getTemplateDetail();
        ec.createEvent();
        ec.updateEvent();
    }

}

