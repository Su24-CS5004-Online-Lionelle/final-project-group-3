// import static org.mockito.ArgumentMatchers.anyString;
// import static org.mockito.Mockito.*;
// import static org.junit.jupiter.api.Assertions.*;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.*;

// import jobplanner.controller.JobPlannerController;
// import jobplanner.model.api.*;
// import jobplanner.model.models.*;
// import jobplanner.model.models.IJobPostModel.*;
// import jobplanner.view.*;

// import java.awt.event.ActionEvent;
// import java.util.List;
// import java.util.Map;
// import java.util.HashMap;
// import java.io.InputStream;
// import java.io.ByteArrayInputStream;

// public class TestJobPlannerController {

//     /** mock the view object. */
//     @Mock
//     private JobPlannerGUI view;

//     /** mock the model object. */
//     @Mock
//     private JobPostModel model;

//     /** mock the saved jobs model object. */
//     @Mock
//     private SavedJobModel savedJobsModel;

//     /** the controller object to be tested. */
//     private JobPlannerController controller;

//     /** set up the test environment. */
//     @BeforeEach
//     public void setUp() {
//         MockitoAnnotations.initMocks(this);
//         controller = new JobPlannerController(model, savedJobsModel, view);
//     }

//     /** Test apply filter action. */
//     @Test
//     public void testApplyFilterAction() {
//         ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Apply Filter");
//         controller.actionPerformed(event);
//         verify(view, times(1)).getFilterPanel();
//     }

//     /** Test reset filter action. */
//     @Test
//     public void testResetFilterAction() {
//         ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Reset Filter");
//         controller.actionPerformed(event);
//         verify(view, times(1)).getFilterPanel();
//     }

//     /** Test add to saved job list action. */
//     @Test
//     public void testAddToSavedJobsAction() {
//         ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Add to Saved Jobs");
//         controller.actionPerformed(event);
//         verify(view, times(1)).getJobListPanel();
//     }

//     /** Test remove from saved job list action. */
//     @Test
//     public void testRemoveSelectedJobsAction() {
//         ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Remove Selected");
//         controller.actionPerformed(event);
//         verify(view, times(1)).getSavedJobsPanel();
//     }

//     /** Test show saved job list panel action. */
//     @Test
//     public void testShowSavedJobsAction() {
//         ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Show Saved Jobs");
//         controller.actionPerformed(event);
//         verify(view, times(1)).showSavedJobsPanel();
//     }

//     /** Test export to csv action. */
//     @Test
//     public void testExportAsCsvAction() {
//         ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Export as CSV");
//         controller.actionPerformed(event);
//         verify(view, times(1)).showErrorDialog(anyString());
//     }

//     /** Test export to txt action. */
//     @Test
//     public void testExportAsTxtAction() {
//         ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "Export as TXT");
//         controller.actionPerformed(event);
//         verify(view, times(1)).showErrorDialog(anyString());
//     }

//     /** Test search action. */
//     @Test
//     public void testSearchJobPostings() {
//         Map<String, String> searchParams = new HashMap<>();
//         searchParams.put("what", "developer");
//         searchParams.put("country", "us");

//         JobPostUtil mockClient = mock(JobPostUtil.class);
//         InputStream mockInputStream = new ByteArrayInputStream("{ \"results\": [] }".getBytes());
//         when(mockClient.getJobPostings("us", searchParams)).thenReturn(mockInputStream);

//         List<JobRecord> jobs = controller.searchJobPostings("us", searchParams);

//         assertNotNull(jobs);
//         assertTrue(jobs.isEmpty());
//     }
// }
