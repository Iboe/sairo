/**
 * Contains all classes to implement the second layer of the control hierarchy. The {@link de.fhb.sailboat.control.navigator.Navigator} <br>
 * is responsible for the course planning. It plans the optimal route for the boat and splits the current <br>
 * {@link de.fhb.sailboat.mission.Task} into simple commands for the {@link de.fhb.sailboat.control.pilot.Pilot}. <br>
 * The navigator decides if beating is necessary and can create and execute the needed tasks for that behavior.
 * 
 * @author hscheel
 */
package de.fhb.sailboat.control.navigator;