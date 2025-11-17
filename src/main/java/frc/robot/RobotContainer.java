// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.subsystems.DrivebaseSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DrivebaseSubsystem driveBase = new DrivebaseSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed. Creates an Xbox controller
  // object.
  private final CommandXboxController driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands. Here it's telling
   * the ArcadeDriveCommand to get the left Y and right X values of the controller to control speed
   * and angle respectively.
   */
  public RobotContainer() {
    getAutonomousCommand().withTimeout(3);
    driveBase.setDefaultCommand(
        new ArcadeDriveCommand(
            driveBase, () -> driverController.getLeftY(), () -> driverController.getRightX()));
  }

  // Sets up the autonomous mode movements for 3 seconds. Specifically tells it to go forward.
  public Command getAutonomousCommand() {
    return new ParallelCommandGroup(new ArcadeDriveCommand(driveBase, () -> 0.0, () -> 3.0));
  }
}
