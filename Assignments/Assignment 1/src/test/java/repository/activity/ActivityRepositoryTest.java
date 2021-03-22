package repository.activity;

import database.DBConnectionFactory;
import model.Activity;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ActivityRepositoryTest {

    private static ActivityRepository activityRepository;

    @BeforeClass
    public static void setup(){
        Connection connection = new DBConnectionFactory().getConnectionWrapper(true).getConnection();
        activityRepository = new ActivityRepository(connection);
    }

    @Before
    public void cleanup(){
        activityRepository.removeAll();
    }

    @Test
    public void save(){
        activityRepository.save("User", "Operation");
        List<Activity> activityList = activityRepository.report("User");
        assertEquals(1, activityList.size());
    }

    @Test
    public void report(){
        activityRepository.save("User", "Operation1");
        activityRepository.save("User", "Operation2");
        List<Activity> activityList = activityRepository.report("User");
        assertEquals(2, activityList.size());
    }



}
