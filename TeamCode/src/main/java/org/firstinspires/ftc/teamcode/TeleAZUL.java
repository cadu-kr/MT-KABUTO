package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "TeleOP-AZUL")
public class TeleAZUL extends LinearOpMode {

    private Rodas rodas;
    private Shooter shooter;
    private ShooterIntake intake;

    @Override
    public void runOpMode(){

        rodas = new Rodas();
        shooter = new Shooter();
        intake = new ShooterIntake();

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
