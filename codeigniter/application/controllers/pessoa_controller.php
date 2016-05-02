<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
 
class Pessoa_Controller extends CI_Controller {
    public function index(){
        $this->load->view('pessoa');
    }
     
    public function cadastrar(){
        $this->load->model('pessoa_model');
        $this->pessoa_model->cadastrar();
        echo "Cadastrado com sucesso!!";
    }
}