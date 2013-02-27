/**
 * Contains all classes related to a {@link de.fhb.sailboat.mission.Mission}. A mission consists of <br>
 * a set of {@link de.fhb.sailboat.mission.Task}s. The tasks can have different degrees of abstraction, <br>
 * for example directly setting a value for an actuator, a command to navigate to specified GPS coordinates <br>
 * or a complex behavior. The tasks do not trigger the behavior needed to fulfill the task, but they contain <br>
 * the information needed for the control logic to execute the tasks.
 * 
 * @author hscheel
 */
package de.fhb.sailboat.mission;