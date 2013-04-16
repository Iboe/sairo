package de.fhb.sailboat.mission;

/***
 * 
 * @author Tobias Koppe
 * @version 1
 */
public enum MissionSystemTextEnum {

	COMPASS_COURSE_TASK{
		public String toString(){
			return "CompassCourseTask";
		}
	},
	
	REACH_CIRCLE_TASK{
		public String toString(){
			return "ReachCircleTask";
		}
	},
	
	BEAT_TASK{
		public String toString(){
			return "BeatTask";
		}
	}, 
	
	PRIMITIVE_COMMAND_TASK{
		public String toString(){
			return "PrimitiveCommandTask";
		}
	}, 
}
