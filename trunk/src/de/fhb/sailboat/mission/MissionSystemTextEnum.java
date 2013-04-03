package de.fhb.sailboat.mission;

/***
 * 
 * @author Tobias Koppe
 * @version 1
 */
public enum MissionSystemTextEnum {

	COMPASSCOURSETASK{
		public String toString(){
			return "CompassCourseTask";
		}
	},
	
	REACHCIRCLETASK{
		public String toString(){
			return "ReachCircleTask";
		}
	},
	
	BEATTASK{
		public String toString(){
			return "BeatTask";
		}
	}
}
