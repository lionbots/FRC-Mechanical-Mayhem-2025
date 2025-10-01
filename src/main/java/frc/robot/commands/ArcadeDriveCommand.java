// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;

/** An example command that uses an example subsystem. */
public class ArcadeDriveCommand extends Command {
  // Sets up variables from imported classes.
  public final DrivebaseSubsystem driveBase;
  private final Supplier<Double> leftSpeed;
  private final Supplier<Double> rightSpeed;

  // Creates a new ArcadeDriveCommand constructor and sets up variables.
  public ArcadeDriveCommand(DrivebaseSubsystem driveBase, Supplier<Double> leftSpeed, Supplier<Double> rightSpeed) {
    this.driveBase = driveBase;
    this.leftSpeed = driveSpeed;
    this.rightSpeed = rightSpeed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Sets the motor speed
    driveBase.setMotorSpeed(leftSpeed.get() * 0.2, rightSpeed.get() * 0.2);

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
