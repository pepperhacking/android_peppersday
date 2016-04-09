using UnityEngine;
using System.Collections;

public class spawnLetter : MonoBehaviour {

    public Sprite[] letters;
    public Transform pref;

	// Use this for initialization
	void Start () {
        StartCoroutine(Example());

    }
	
	// Update is called once per frame
	void Update () {


        

    }


    IEnumerator Example()
    {
        print(Time.time);
        yield return new WaitForSeconds(0.3f);
        print(Time.time);
        Transform spawned = (Transform)Instantiate(pref, new Vector3(Random.Range(-7.0F, 7.0F), 3, 0), Quaternion.identity);

        spawned.GetComponent<SpriteRenderer>().sprite = letters[Random.Range(0, 26)];
        StartCoroutine(Example());
    }



}
  

