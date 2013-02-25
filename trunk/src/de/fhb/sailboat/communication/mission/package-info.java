/**
 * 
 */
/**
 * Involves all classes and interfaces that are responsible for the mission serialization, in order to transfer it over the communications channel.<br>
 * {@link de.fhb.sailboat.communication.mission.SerializedTask} defines the interface for serialized tasks.<br>
 * {@link de.fhb.sailboat.communication.mission.SerializedTaskBase} implements the  methods of the interface {@link de.fhb.sailboat.communication.mission.SerializedTask}. They're common for all {@link de.fhb.sailboat.communication.mission.SerializedTask} classes.<br>
 * Furthermore it defines two abstract methods, which are supposed to de-/serialize concrete task data. Those methods are implemented by concrete {@link de.fhb.sailboat.communication.mission.SerializedTask} classes extending {@link de.fhb.sailboat.communication.mission.SerializedTaskBase}.
 * The {@link de.fhb.sailboat.communication.mission.TaskSerializer} maintains a mapping of {@link de.fhb.sailboat.mission.Task}and {@link de.fhb.sailboat.communication.mission.SerializedTask} classes. 
 * It's responsible for serializing and deserializing given {@link de.fhb.sailboat.mission.Task} objects, using the corresponding {@link de.fhb.sailboat.communication.mission.SerializedTask} classes.
 *  
 * 
 * @author Michael kant
 *
 */
package de.fhb.sailboat.communication.mission;