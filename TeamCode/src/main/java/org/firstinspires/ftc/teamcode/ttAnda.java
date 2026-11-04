package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class ttAnda extends OpMode {
    private DcMotor rodaFrenteDireita, rodaFrenteEsquerda, rodaTrasDireita, rodaTrasEsquerda;


    public void andar() {

        double comandoFrente = -gamepad1.left_stick_y;
        double comandoLateral = gamepad1.left_stick_x;
        double comandoGiro = gamepad1.right_stick_x;

        double limite;

        if (gamepad1.b) {
            limite = 1;
        }
        else {
            limite = .7;
        }


        double potenciaFrenteEsquerda = (comandoFrente + comandoLateral + comandoGiro) * limite;
        double potenciaFrenteDireita = (comandoFrente - comandoLateral - comandoGiro) * limite;
        double potenciaTrasEsquerda = (comandoFrente - comandoLateral + comandoGiro) * limite;
        double potenciaTrasDireita = (comandoFrente + comandoLateral - comandoGiro) * limite;

        rodaFrenteDireita.setPower(potenciaFrenteDireita);
        rodaFrenteEsquerda.setPower(potenciaFrenteEsquerda);
        rodaTrasEsquerda.setPower(potenciaTrasEsquerda);
        rodaTrasDireita.setPower(potenciaTrasDireita);
    }


    @Override
    public void init() {

        rodaFrenteDireita = hardwareMap.get(DcMotor.class, "F-D");
        rodaFrenteEsquerda = hardwareMap.get(DcMotor.class, "F-E");
        rodaTrasDireita = hardwareMap.get(DcMotor.class, "T-D");
        rodaTrasEsquerda = hardwareMap.get(DcMotor.class, "T-E");

        rodaFrenteDireita.setDirection(DcMotorSimple.Direction.REVERSE);
        rodaTrasDireita.setDirection(DcMotorSimple.Direction.REVERSE);

        rodaFrenteDireita.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaFrenteEsquerda.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaTrasDireita.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaTrasEsquerda.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    @Override
    public void loop() {
        andar();
    }
}
