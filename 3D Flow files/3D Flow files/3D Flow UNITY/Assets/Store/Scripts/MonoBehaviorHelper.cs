
using UnityEngine;
using System.Collections;

/// <summary>
/// Class to prevent some duplicate code
/// </summary>
namespace StoreScripts
{
	public class MonoBehaviorHelper : MonoBehaviour 
	{
		private GameManager _gameManager;
		public GameManager gameManager
		{
			get
			{
				if (_gameManager == null)
					_gameManager = FindObjectOfType<GameManager> ();

				return _gameManager;
			}
		}

		private PlayerManager _playerManager;
		public PlayerManager playerManager
		{
			get
			{
				if (_playerManager == null)
					_playerManager = FindObjectOfType<PlayerManager> ();

				return _playerManager;
			}
		}

		private Transform _playerTransform;
		public Transform playerTransform
		{
			get
			{
				if (playerManager == null)
					return null;

				if (_playerTransform == null)
					_playerTransform = playerManager.transform;

				return _playerTransform;
			}
		}

	

		private Camera _cam;
		public Camera cam
		{
			get
			{
				if (_cam == null)
					_cam = Camera.main;

				return _cam;
			}
		}

	

		private Transform _camTransform;
		public Transform camTransform
		{
			get
			{
				if (_camTransform == null)
					_camTransform = Camera.main.transform;

				return _camTransform;
			}
		}


	


		private CanvasManager _canvasManager;
		public CanvasManager canvasManager
		{
			get
			{
				if (_canvasManager == null)
					_canvasManager = FindObjectOfType<CanvasManager> ();

				return _canvasManager;
			}
		}
	}
}