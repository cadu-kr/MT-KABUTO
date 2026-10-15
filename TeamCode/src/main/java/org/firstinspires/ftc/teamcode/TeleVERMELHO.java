package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOP-VERMELHO")
public class TeleVERMELHO extends LinearOpMode {

    private Rodas2 rodas;
    private Shooter shooter;
    private ShooterIntake2 intake;

    @Override
    public void runOpMode(){

        rodas = new Rodas2();
        shooter = new Shooter();
        intake = new ShooterIntake2();

        rodas.inicio(hardwareMap);
        shooter.inicio(hardwareMap);
        intake.inicio(hardwareMap);

        waitForStart();
        while (opModeIsActive()){

            rodas.rodar(gamepad1);
            shooter.rodar(gamepad1);
            intake.rodar(gamepad1);

            intake.telemetry(telemetry);
            shooter.telemetry(telemetry);

            telemetry.update();

        }
    }
}
