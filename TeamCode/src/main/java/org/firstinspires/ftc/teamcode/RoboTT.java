package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ROBO-TOTAL")
public class RoboTT extends OpMode {

    Lancador lancador = new Lancador();
    Intake intake = new Intake();
    Rodas rodas = new Rodas();

    @Override
    public void init() {

        lancador.inicializar(hardwareMap);
        intake.inicializar(hardwareMap);
        rodas.inicializar(hardwareMap);
    }

    @Override
    public void loop() {

        lancador.rodar(gamepad2);
        intake.rodar(gamepad2);
        rodas.rodar(gamepad1);
    }
}
