// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArcadeDriveCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.OuttakeCommand;
import frc.robot.subsystems.DrivebaseSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.OuttakeSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final OuttakeSubsystem outtake = new OuttakeSubsystem();
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final DrivebaseSubsystem driveBase = new DrivebaseSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController operatorController =
      new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  private final CommandXboxController driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    driveBase.setDefaultCommand(
        new ArcadeDriveCommand(
            driveBase, () -> driverController.getLeftY(), () -> driverController.getRightX()));
    configureBindings();
    getAutonomousCommand().withTimeout(3);
  }

  private void configureBindings() {
    operatorController
        .rightTrigger(0.1)
        .whileTrue(new OuttakeCommand(outtake, operatorController::getRightTriggerAxis));
    System.out.println("fer");
    // operatorController.rightTrigger(0).whileTrue(Commands.run(() -> System.out.println("they dont pay me enough")));
    operatorController
        .leftTrigger(0.1)
        .whileTrue((new IntakeCommand(intake, operatorController::getLeftTriggerAxis)));
    operatorController.leftBumper().whileTrue((new IntakeCommand(intake, () -> 0.7)));
    operatorController.rightBumper().whileTrue((new OuttakeCommand(outtake, () -> -0.7)));
  }

  // Sets up the autonomous mode movements for 3 seconds.
  public Command getAutonomousCommand() {
    // TODO: Add basic algorithm to perform autonomous operation for the first 3 seconds.
    return new ParallelCommandGroup(
        new ArcadeDriveCommand(driveBase, () -> -0.3, () -> -0.3));
  }
}
