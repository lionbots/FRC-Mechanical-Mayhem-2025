// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.OuttakeConstants;
import com.revrobotics.spark.*;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class OuttakeSubsystem extends SubsystemBase {
  private final SparkMax outtakeMotor = new SparkMax(OuttakeConstants.outtakeMotorPort, MotorType.kBrushless);
  /** Creates a new ExampleSubsystem. */
  public OuttakeSubsystem() {
    // calls brake mode method
    setMotorIdleMode();
  }

  /** Method sets outtake motor brake*/
  public void setMotorIdleMode(){
    SparkMaxConfig idleMode = new SparkMaxConfig();
    idleMode.idleMode(IdleMode.kBrake);

    outtakeMotor.configure(idleMode, com.revrobotics.spark.SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }
  
  public void setOuttakeSpeed(double outtakeSpeed) {
    outtakeMotor.set(outtakeSpeed);
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
