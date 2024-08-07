import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import jobplanner.controller.JobPlannerController;
import jobplanner.model.models.ISavedJobModel;
import jobplanner.view.JobPlannerGUI;
import jobplanner.view.panels.FilterPanel;
import jobplanner.view.panels.JobListPanel;
import jobplanner.view.panels.SavedJobsPanel;

/**
 * Unit tests for the JobPlannerController class.
 */
public class TestJobPlannerController {

    /** mock the view object. */
    @Mock
    private JobPlannerGUI mockView;

    /** mock the saved jobs model object. */
    @Mock
    private ISavedJobModel mockModel;

    /** mock the filter panel object. */
    @Mock
    private FilterPanel filterPanel;

    /** mock the job list panel object. */
    @Mock
    private JobListPanel jobListPanel;

    /** mock the saved jobs panel object. */
    @Mock
    private SavedJobsPanel savedJobsPanel;

    /** the controller object to be tested. */
    private JobPlannerController controller;

    /** set up the test environment. */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockView.getFilterPanel()).thenReturn(filterPanel);
        when(mockView.getJobListPanel()).thenReturn(jobListPanel);
        when(mockView.getSavedJobsPanel()).thenReturn(savedJobsPanel);
        controller = new JobPlannerController(mockModel, mockView);
    }

    /** test the start method. */
    @Test
    void testStart() {
        controller.start();
        verify(mockView).setVisible(true);
    }
}
