// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.OuttakeCommand;
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
  private final IntakeSubsystem intake = new IntakeSubsystem();
  private final OuttakeSubsystem outtake = new OuttakeSubsystem();

  private final CommandXboxController operatorController =
      new CommandXboxController(OperatorConstants.kOperatorControllerPort);
  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController OperatorController =
      new CommandXboxController(OperatorConstants.kOperatorControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    getAutonomousCommand().withTimeout(3);
    configureBindings();
  }

  private void configureBindings() {
    operatorController
        .rightTrigger(0.1)
        .whileTrue(new OuttakeCommand(outtake, operatorController::getRightTriggerAxis));
    operatorController
        .leftTrigger(0.1)
        .whileTrue((new IntakeCommand(intake, operatorController::getLeftTriggerAxis)));
    operatorController.leftBumper().whileTrue((new IntakeCommand(intake, () -> 0.7)));
    operatorController.rightBumper().whileTrue((new OuttakeCommand(outtake, () -> -0.7)));
  }

  public Command getAutonomousCommand() {
    return new ParallelCommandGroup(new OuttakeCommand(outtake, () -> 0.5));
  }
}
