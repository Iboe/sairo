/**
 * Contains the first three control layers, which implement the control logic. The control architecture <br>
 * consists of four hierarchic layers. Each layer does only know the layer directly below it. Each layer <br>
 * is responsible for a subtask of the whole control process and send commands to the layer directly below <br>
 * it. The higher a layer is settled in the hierarchie, the higher is the degree of abstraction of the <br>
 * its subtasks. <br><br>
 * Every layer has access to all data of the {@link de.fhb.sailboat.worldmodel.WorldModel}. All layers except <br>
 * the lowest are run concurrently in specified intervals. The fourth layer is a hardware abstraction layer <br>
 * and settled in the package de.fhb.sailboat.serial.actuator, since it realizes the communication with the <br>
 * actuators.
 * 
 * @author hscheel
 */
package de.fhb.sailboat.control;