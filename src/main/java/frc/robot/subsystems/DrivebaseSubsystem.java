// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonSRX;
import frc.robot.subsystems.DrivebaseSubsystem;

public class DrivebaseSubsystem extends SubsystemBase {
  private final PWMTalonSRX rightWheel = new PWMTalonSRX(DriveConstants.rightWheel);
  private final PWMTalonSRX leftWheel = new PWMTalonSRX(DriveConstants.leftWheel);
  private final DifferentialDrive differentialDrive = new DifferentialDrive(leftWheel, rightWheel);          
  /** Creates a new DrivebaseSubsystem. */
    public DrivebaseSubsystem(){
      setMotorInverted();
  }

  public void setMotorInverted() {
    rightWheel.setInverted(true);
  }

  //Create a method for driving
  public void setMotorSpeed(double leftSpeed, double rightSpeed){
    rightWheel.set(rightSpeed);
    leftWheel.set(leftSpeed);
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
