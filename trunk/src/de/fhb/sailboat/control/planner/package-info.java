/**
 * Contains all classes to implement the first and highest layer of the control hierarchy. The <br>
 * {@link de.fhb.sailboat.control.planner.Planner} is responsible for the path planning. It <br>
 * arranges the order of the {@link de.fhb.sailboat.mission.Task}s of the {@link de.fhb.sailboat.mission.Mission} <br>
 * to execute. Therefore, the planner has to check if a task is already finished. The planner also <br>
 * starts the execution of the current task. The planner can create additional tasks and add them to <br>
 * the mission. 
 *   
 * @author hscheel
 */
package de.fhb.sailboat.control.planner;