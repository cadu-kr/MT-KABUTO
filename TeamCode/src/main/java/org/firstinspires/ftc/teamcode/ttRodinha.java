package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TT-Rodinha", group = "TESTE")
public class ttRodinha extends OpMode {

    private DcMotor rodaFrenteDireita, rodaFrenteEsquerda, rodaTrasDireita, rodaTrasEsquerda;
    private boolean ativo = false;
    private boolean travado = false;

    ElapsedTime temp = new ElapsedTime();

    public void rodinhas() {
        double comandoFrente = -gamepad1.left_stick_y;
        double comandoLateral = gamepad1.left_stick_x;
        double comandoGiro = gamepad1.right_stick_x;

        boolean empurrando = Math.hypot(comandoFrente, comandoLateral) > .1;

        if (!empurrando) {
            ativo = false;
            travado = false;
        } else if (!travado) {
            if (!ativo) {
                ativo = true;
                temp.reset();
            } else if (temp.seconds() >= .3) {
                ativo = false;
                travado = true;
            }
        }

        double fator;

        if (ativo) {
            fator = .7;
        } else {
            fator = 1;
        }

        double potenciaFrenteEsquerda = comandoFrente + comandoLateral + comandoGiro;
        double potenciaFrenteDireita = comandoFrente - comandoLateral - comandoGiro;
        double potenciaTrasEsquerda = comandoFrente - comandoLateral + comandoGiro;
        double potenciaTrasDireita = comandoFrente + comandoLateral - comandoGiro;

        double maior = Math.max(1.0, Math.max(Math.max(Math.abs(potenciaFrenteEsquerda), Math.abs(potenciaFrenteDireita)),
                Math.max(Math.abs(potenciaTrasEsquerda), Math.abs(potenciaTrasDireita))));

        rodaFrenteDireita.setPower(potenciaFrenteDireita / maior * fator);
        rodaFrenteEsquerda.setPower(potenciaFrenteEsquerda / maior * fator);
        rodaTrasEsquerda.setPower(potenciaTrasEsquerda / maior * fator);
        rodaTrasDireita.setPower(potenciaTrasDireita / maior * fator);

    }

    @Override
    public void init() {
        rodaFrenteDireita = hardwareMap.get(DcMotor.class, "F-D");
        rodaFrenteEsquerda = hardwareMap.get(DcMotor.class, "F-E");
        rodaTrasDireita = hardwareMap.get(DcMotor.class, "T-D");
        rodaTrasEsquerda = hardwareMap.get(DcMotor.class, "T-E");

        rodaFrenteDireita.setDirection(DcMotorSimple.Direction.REVERSE);
        rodaTrasDireita.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        rodinhas();
    }
}