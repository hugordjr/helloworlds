<!doctype html>
<html>
<head>
<title>Requisições Ajax com CodeIgniter</title>
 
<script type="text/javascript"
    src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js">
</script>
 
<script type="text/javascript">
<script type="text/javascript">
$(document).ready(function(){
    $("#cadastrar").click(function(){
            //alert("ola");
            $.ajax({
                    url: '<?= base_url(); ?>' + 'index.php/pessoa_controller/cadastrar',
                    type: 'POST',
                    data: $("#formulario_cadastro").serialize(),
                    success: function(msg){
                        $("#mensagem").html(msg);
                        if(msg == 'Cadastrado com sucesso!!'){
                            jQuery.fn.reset = function(){
                                    $(this).each(function(){ this.reset();});
                                }
                            $("#formulario_cadastro").reset();
                        }
                        }
                });
            return false;
        });
});     
</script>
</script>
</head>
 
<body>
 
    <div id="mensagem"></div>
    <form id="formulario_cadastro">
 
    <?php echo form_label('Nome', 'nome');?>
    <?php echo form_input(array(
                "name" => "ds_nomecompleto",
                "id" => "ds_nomecompleto",
                "type" => "text",
                "placeholder" => "Nome"
                ));?>
 
                <?php echo form_label('Sobrenome', 'nome');?>
                <?php echo form_input(array(
                "name" => "dt_datanascimento",
                "id" => "dt_datanascimento",
                "type" => "text",
                "placeholder" => "Sobrenome"
                ));?>
 
                <?php echo form_label('Usuario', 'usuario');?>
                <?php echo form_input(array(
                "name" => "ds_login",
                "id" => "ds_login",
                "type" => "text",
                "placeholder" => "Usuario"
                ));?>
 
                <?php echo form_label('Senha', 'senha');?>
                <?php echo form_password(array(
                "name" => "ds_senha",
                "id" => "ds_senha",
                "type" => "text",
                "placeholder" => "Senha"
                ));?>
 
                <?php echo form_input(array(
                "name" => "cadastrar",
                "id" => "cadastrar",
                "type" => "submit"
                ), "Cadastrar");?>
 
    </form>
 
</body>
</html>