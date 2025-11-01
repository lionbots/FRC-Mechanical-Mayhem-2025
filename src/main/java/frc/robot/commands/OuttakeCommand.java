package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.OuttakeSubsystem;
import java.util.function.Supplier;

/** An example command that uses an example subsystem. */
public class OuttakeCommand extends Command {
  private final OuttakeSubsystem outtake;
  private final Supplier<Double> outtakeSpeed;

  public OuttakeCommand(OuttakeSubsystem outtake, Supplier<Double> outtakeSpeed) {
    this.outtake = outtake;
    this.outtakeSpeed = outtakeSpeed;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    outtake.setOuttakeSpeed(outtakeSpeed.get() * -0.7);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    outtake.setOuttakeSpeed(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
