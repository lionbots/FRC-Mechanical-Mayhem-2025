// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DrivebaseSubsystem;
import java.util.function.Supplier;

// Sets up ArcadeDriveCommand class to control the robot's movement, used in RobotContainer.
public class ArcadeDriveCommand extends Command {
  // Sets up variables from imported classes.
  public final DrivebaseSubsystem driveBase;
  private final Supplier<Double> angle;
  private final Supplier<Double> rightSpeed;

  // Creates a new ArcadeDriveCommand constructor and sets up variables.
  public ArcadeDriveCommand(
      DrivebaseSubsystem driveBase, Supplier<Double> angle, Supplier<Double> rightSpeed) {
    this.driveBase = driveBase;
    this.angle = angle;
    // Declared rightSpeed as leftSpeed is given by a negative value of rightSpeed.
    this.rightSpeed = rightSpeed;
    // Adds the driveBase subsystem as a requirement for the ArcadeDriveCommand, ensuring no other
    // commands can use it simultaneously.
    this.addRequirements(this.driveBase);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveBase.arcadeDrive(rightSpeed.get(), angle.get());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // Sets the motors speed to zero.
    driveBase.setMotorSpeed(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
