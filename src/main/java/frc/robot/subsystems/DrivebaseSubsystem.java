// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

// Imports the libraries need for the code, including the DifferentialDrive and the SparkMax
// objects.
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

// Class to define the DrivebaseSubsystem, named after the Java file.
public class DrivebaseSubsystem extends SubsystemBase {
  // Sets up the 2 motors as instances of SparkMax.
  private final SparkMax rightfrontWheel =
      new SparkMax(DriveConstants.rightfrontWheel, MotorType.kBrushless);
  private final SparkMax leftfrontWheel =
      new SparkMax(DriveConstants.leftfrontWheel, MotorType.kBrushless);
  // Sets up a differential drive.
  private final DifferentialDrive differentialDrive =
      new DifferentialDrive(leftfrontWheel, rightfrontWheel);
  private final PIDController PID =
      new PIDController(
          DriveConstants.PIDConstants.kP,
          DriveConstants.PIDConstants.kI,
          DriveConstants.PIDConstants.kD);
  /** Creates a new DrivebaseSubsystem. */
  public DrivebaseSubsystem() {
    configurePID();
    setMotorInverted();
  }

  // Sets the left wheel inverted using a config
  public void setMotorInverted() {
    SparkMaxConfig config = new SparkMaxConfig();
    config.inverted(true);
    leftfrontWheel.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
  }

  public void arcadeDrive(double speed, double angle) {
    this.differentialDrive.arcadeDrive(speed, angle);
  }

  // Creates a method to easily set the motor speeds.
  public void setMotorSpeed(double leftSpeed, double rightSpeed) {
    rightfrontWheel.set(rightSpeed);
    leftfrontWheel.set(leftSpeed);
  }

  private void configurePID() {
    PID.enableContinuousInput(-180, 180);
    PID.setTolerance(DriveConstants.PIDConstants.tolerance);
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
