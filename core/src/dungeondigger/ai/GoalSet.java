package dungeondigger.ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GoalSet {
	private List<Objective>						objectives		= new ArrayList<>();
	private final Comparator<? super Objective>	titleSort		= ( one, two ) -> one.getTitle().compareToIgnoreCase( two.getTitle() );
	private final Comparator<? super Objective>	prioritySort	= ( one, two ) -> two.getPriorityLevel() - one.getPriorityLevel();		;

	public void add( Objective newObjective ) {
		objectives.add( newObjective );

		objectives = objectives.stream()
				.filter( ( x ) -> x != null )
				.sorted( titleSort )
				.sorted( prioritySort )
				.distinct()
				.collect( Collectors.toList() );
	}
}
