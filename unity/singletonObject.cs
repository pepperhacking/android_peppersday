using UnityEngine;
using System.Collections;

public class singletonObject : MonoBehaviour {

    private static singletonObject _instance;
    private int i = 0;



// Use this for initialization
void Start () {
        DontDestroyOnLoad(this);


}

    void Update()
    {

    }

void Awake()
    {
        //if we don't have an [_instance] set yet
        if (!_instance)
            _instance = this;
        //otherwise, if we do, kill this thing
        else
            Destroy(this.gameObject);


        DontDestroyOnLoad(this.gameObject);
    }

    void ReceiveRotDir(string message)
    {
        if(message == "word")
        {
            Application.LoadLevel(1);
        }else if (message == "speak")
        {
            Application.LoadLevel(2);
        }
        else if (message == "sing")
        {
            Application.LoadLevel(3);
        }
        else if (message == "letter")
        {
            Application.LoadLevel(4);
        }
        else if (message == "yoursound")
        {
            Application.LoadLevel(5);
        }
        else if (message == "beat")
        {
            Application.LoadLevel(6);
        }
        else if (message == "silence")
        {
            Application.LoadLevel(7);
        }
        else
        {
            if(message != null)
            {
                GameObject.Find("google").GetComponent<googleSearch>().DownloadImage(message);// = message;
            } 
        }
    }

}
