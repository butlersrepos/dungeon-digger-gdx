package dungeondigger.ai;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

@Slf4j
public class GoalSetTest {

	private GoalSet					subject;

	private Objective				unimportantObjective;

	private Objective				newObjective;

	private ArrayList<Objective>	objectiveList;

	private String					objectiveTitle;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks( this );

		objectiveList = new ArrayList<Objective>();

		subject = GoalSet.builder().objectives( objectiveList ).build();

		setupObjectives();
	}

	@Test
	public void shouldAddObjectives() throws Exception {
		subject.add( newObjective );
		assertEquals( 1, subject.getObjectives().size() );
	}

	@Test
	public void addObjectiveShouldIgnoreAlreadyPresentOnes() throws Exception {
		subject.add( newObjective );
		subject.add( newObjective );
		assertEquals( 1, subject.getObjectives().size() );
	}

	@Test
	public void addedIdenticalObjectivesShouldNotStay() throws Exception {
		subject.add( unimportantObjective );
		subject.add( newObjective );

		assertEquals( 1, subject.getObjectives().size() );
	}

	@Test
	public void differentObjectivesShouldBeOrderedByPriority() throws Exception {
		Objective third = newObjective.withTitle( "Aew Title" );
		third.setPriorityLevel( 8 );

		subject.add( newObjective );
		subject.add( third );
		subject.add( unimportantObjective );

		assertEquals( 20, subject.getObjectives().get( 0 ).getPriorityLevel() );
	}

	private void setupObjectives() {
		objectiveTitle = "Same Title";
		newObjective = Objective.builder().priorityLevel( 20 ).title( objectiveTitle ).build();
		unimportantObjective = Objective.builder().priorityLevel( 2 ).title( objectiveTitle ).build();
	}
}
